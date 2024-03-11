import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhsFinanceComponent } from './dhs-finance.component';

describe('DhsFinanceComponent', () => {
  let component: DhsFinanceComponent;
  let fixture: ComponentFixture<DhsFinanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DhsFinanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DhsFinanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
