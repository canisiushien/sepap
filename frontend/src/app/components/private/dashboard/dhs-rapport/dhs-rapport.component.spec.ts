import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhsRapportComponent } from './dhs-rapport.component';

describe('DhsRapportComponent', () => {
  let component: DhsRapportComponent;
  let fixture: ComponentFixture<DhsRapportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DhsRapportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DhsRapportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
