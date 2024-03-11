import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreeColumnLayoutComponent } from './three-column-layout.component';

describe('ThreeColumnLayoutComponent', () => {
  let component: ThreeColumnLayoutComponent;
  let fixture: ComponentFixture<ThreeColumnLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThreeColumnLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreeColumnLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
