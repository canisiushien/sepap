import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrillePerformanceComponent } from './grille-performance.component';

describe('GrillePerformanceComponent', () => {
  let component: GrillePerformanceComponent;
  let fixture: ComponentFixture<GrillePerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GrillePerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GrillePerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
